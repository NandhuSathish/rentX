import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import KeenSlider, { KeenSliderInstance, KeenSliderPlugin } from 'keen-slider';
import { UserProductService } from '../../services/user-product.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import flatpickr from 'flatpickr';

//keenSlider config.
function ThumbnailPlugin(main: KeenSliderInstance): KeenSliderPlugin {
  return (slider) => {
    function removeActive() {
      slider.slides.forEach((slide) => {
        slide.classList.remove('active');
      });
    }
    function addActive(idx: number) {
      slider.slides[idx].classList.add('active');
    }

    function addClickEvents() {
      slider.slides.forEach((slide, idx) => {
        slide.addEventListener('click', () => {
          main.moveToIdx(idx);
        });
      });
    }

    slider.on('created', () => {
      addActive(slider.track.details.rel);
      addClickEvents();
      main.on('animationStarted', (main) => {
        removeActive();
        const next = main.animator.targetIdx || 0;
        addActive(main.track.absToRel(next));
        slider.moveToIdx(Math.min(slider.track.details.maxIdx, next));
      });
    });
  };
}

// Allow only numbers and certain special keys
(window as any).onlyNumbers = function (event: any) {
  const key = event.key;
  if (!/^[0-9\s\b]+$/.test(key) && !isSpecialKey(event)) {
    event.preventDefault();
    return false;
  }
  return true;
};
function isSpecialKey(event: any) {
  const specialKeys = ['Backspace', 'Delete', 'ArrowLeft', 'ArrowRight', 'Tab'];
  return specialKeys.includes(event.key);
}

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
})
export class ProductDetailComponent implements OnInit {
  @ViewChild('sliderRef') sliderRef!: ElementRef<HTMLElement>;
  @ViewChild('thumbnailRef') thumbnailRef!: ElementRef<HTMLElement>;
  @ViewChild('featureList') featureList!: ElementRef<HTMLElement>;
  @ViewChild('dateRangeInput') dateRangeInput!: ElementRef;
  @ViewChild('calendarContainer') calendarContainer!: ElementRef;
  maxDate!: Date;
  slider!: KeenSliderInstance;
  thumbnailSlider!: KeenSliderInstance;
  productDetail: any;
  productImageList: any;
  categoryId: any;
  productId: any;
  numberOfDays: any;
  isAvailable: boolean = false;
  range = new FormGroup({
    start: new FormControl<Date | null>(null, Validators.required),
    end: new FormControl<Date | null>(null, Validators.required),
    qty: new FormControl<string | null>('1', [
      Validators.required,
      Validators.pattern('^[0-9]+$'),
    ]),
  });

  constructor(
    private productService: UserProductService,
    private activatedRoute: ActivatedRoute
  ) {
    // const currentYear = new Date().getFullYear();
    // this.maxDate = new Date(currentYear + 1, 11, 31);
    // this.range.valueChanges.subscribe(() => {
    //   if (this.range.valid) {
    //     this.logSelectedRange();
    //   }
    // });
  }
  // flatpickrInstance: flatpickr.Instance;
  flatpickrInstance: flatpickr.Instance | null = null;

  // logSelectedRange() {
  //   console.log('Selected range:', this.range.value);
  // }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.productId = params['pid'];
    });

    this.productService.getProductById(this.productId).subscribe((data) => {
      this.productDetail = data;
      this.productImageList = data.images;
      this.categoryId = data.categoryId;
      this.productImageList = Object.entries(this.productImageList[0]).map(
        ([key, value]) => ({ [key]: value })
      );
    });
  }

  ngAfterViewInit() {
    const today = new Date();
    flatpickr(this.dateRangeInput.nativeElement, {
      mode: 'range',
      minDate: today,
      dateFormat: 'd-m-Y',
      // position: 'auto right',
      onChange: (selectedDates) => {
        // this.numberOfDays = selectedDates.length;
        console.log(selectedDates);
        if (selectedDates.length === 2) {
          const start = selectedDates[0];
          const end = selectedDates[1];
          const differenceInTime = end.getTime() - start.getTime();
          this.numberOfDays =
            Math.floor(differenceInTime / (1000 * 3600 * 24)) + 1;
        } else {
          this.numberOfDays = null;
        }
        this.isAvailable = true;
      },
    });
    // this.flatpickrInstance = flatpickr(this.startDateInput.nativeElement);

    this.slider = new KeenSlider(this.sliderRef.nativeElement);
    this.thumbnailSlider = new KeenSlider(
      this.thumbnailRef.nativeElement,
      {
        initial: 0,
        slides: {
          perView: 4,
          spacing: 2,
        },
        // loop: true,
      },
      [ThumbnailPlugin(this.slider)]
    );
  }

  toggleElement(element: HTMLElement) {
    element.classList.toggle('hidden');
  }
  hasHiddenClass(element: HTMLElement): boolean {
    return element.classList.contains('hidden');
  }
  incrementQuantity() {
    const qtyControl = this.range.get('qty');
    let currentQty = 0;
    if (qtyControl?.value == '') {
      currentQty++;
      qtyControl.setValue(currentQty.toString());
      return;
    }
    if (qtyControl && typeof qtyControl.value === 'string') {
      // let currentQty = qtyControl.value;
      let currentQty = parseInt(qtyControl.value, 10);
      if (currentQty >= 999999) {
        return;
      }

      currentQty++;
      // qtyControl.setValue(currentQty);
      qtyControl.setValue(currentQty.toString());
      console.log(qtyControl.value, 'qty value');
    }
  }
  decrementQuantity() {
    const qtyControl = this.range.get('qty');

    if (qtyControl?.value == '') {
      return;
    }
    if (qtyControl && typeof qtyControl.value === 'string') {
      // let currentQty = qtyControl.value;
      let currentQty = parseInt(qtyControl.value, 10);
      if (currentQty <= 1) {
        return;
      }
      currentQty--;
      // qtyControl.setValue(currentQty);
      qtyControl.setValue(currentQty.toString());
      console.log(qtyControl.value, 'qty value');
    }
  }

  ngOnDestroy() {
    if (this.slider) this.slider.destroy();
    if (this.thumbnailSlider) this.thumbnailSlider.destroy();
  }

  dateFilterFn = (date: Date) => ![0, 6].includes(date.getDay());
  getImageUrl(image: any, index: number): string {
    const imageKey = 'image' + (index + 1);
    return image[imageKey];
  }
  onSubmit() {
    console.log(this.range.value, 'form values');
  }
}
