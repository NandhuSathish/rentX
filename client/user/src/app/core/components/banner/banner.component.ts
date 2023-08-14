import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import KeenSlider, { KeenSliderInstance } from 'keen-slider';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css'],
})
export class BannerComponent implements OnInit {
  @ViewChild('sliderRef') sliderRef!: ElementRef<HTMLElement>;
  slider: KeenSliderInstance | null = null;
  constructor() {}

  ngOnInit(): void {}
  ngAfterViewInit(): void {
    this.initSlider();
  }

  private initSlider(): void {
    this.slider = new KeenSlider(
      this.sliderRef.nativeElement,
      {
        loop: true,
      },
      [
        (slider) => {
          let timeout: any;
          let mouseOver = false;
          function clearNextTimeout() {
            clearTimeout(timeout);
          }
          function nextTimeout() {
            clearTimeout(timeout);
            if (mouseOver) return;
            timeout = setTimeout(() => {
              slider.next();
            }, 1000);
          }
          slider.on('created', () => {
            slider.container.addEventListener('mouseover', () => {
              mouseOver = true;
              clearNextTimeout();
            });
            slider.container.addEventListener('mouseout', () => {
              mouseOver = false;
              nextTimeout();
            });
            nextTimeout();
          });
          slider.on('dragStarted', clearNextTimeout);
          slider.on('animationEnded', nextTimeout);
          slider.on('updated', nextTimeout);
        },
      ]
    );
  }
}
