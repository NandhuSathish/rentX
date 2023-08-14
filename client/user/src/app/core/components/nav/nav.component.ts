import {
  Component,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserProductService } from 'src/app/features/pos/services/user-product.service';
import { AuthService } from 'src/app/services/auth.service';
import { AlertService } from 'src/app/shared/services/alert.service';
import { CartStateService } from 'src/app/shared/states/cart-state.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit {
  @ViewChild('sidebar') sidebar!: ElementRef;
  @ViewChild('megaMenu') megaMenu!: ElementRef;
  @ViewChild('profileMenu') profileMenu!: ElementRef;
  @ViewChild('searchInput', { static: false }) searchInput!: ElementRef;
  @ViewChild('searchInputMobile', { static: false })
  searchInputMobile!: ElementRef;

  isAuthenticated: boolean = false;
  Message = 'Are you sure you want to log out ?';
  searchQuery: string = '';
  isMenuOpen: boolean = false;
  @Input() searchParam!: string;
  cartCount: number = 0;
  constructor(
    private authService: AuthService,
    public alertService: AlertService,
    private userProductService: UserProductService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cartService: CartStateService
  ) {}

  ngOnInit(): void {
    // localStorage.setItem('cartCount','0');
    this.isAuthenticated = this.authService.isAuthenticated();
    console.log(this.searchParam, 'searc param');
    if (this.searchParam == undefined) {
      this.searchParam = '';
    }
    // this.searchParam = decodeURIComponent(this.searchParam);
    this.cartService.cartCount$.subscribe((count) => {
      // alert(count)
      this.cartCount = count;
    });
  }

  logOut() {
    this.alertService.openAlert(this.Message).subscribe((res) => {
      if (res) {
        this.authService.logOut();
        this.toggleprofileMenu();
        this.ngOnInit();
      } else {
        return;
      }
    });
  }
  toggleSidebar() {
    const sidebarEl = this.sidebar.nativeElement;
    sidebarEl.classList.toggle('hidden');
  }
  toggleMegaMenu() {
    const megaMenuEl = this.megaMenu.nativeElement;
    megaMenuEl.classList.toggle('hidden');
  }
  toggleprofileMenu() {
    const profileMenuEl = this.profileMenu.nativeElement;
    profileMenuEl.classList.toggle('hidden');
  }

  // isMenuOpened: boolean = false;

  toggleMenu(): void {
    const profileMenuEl = this.profileMenu?.nativeElement;
    profileMenuEl.classList.add('hidden');
  }

  // clickedOutside(): void {
  //   this.isMenuOpened = false;
  // }

  onSearch(searchQuery: any) {
    // this.userProductService
    //   .searchProduct(
    //     undefined,
    //     undefined,
    //     undefined,
    //     undefined,
    //     searchQuery,
    //     undefined,
    //     undefined
    //   )
    //   .subscribe((res: any) => {
    //   });
    let queryParams: {
      search?: string;
    } = {};
    queryParams.search = encodeURIComponent(searchQuery);
    // queryParams.search = searchQuery;
    this.router.navigate(['/products'], { queryParams });
  }
  clearSearch() {
    this.searchParam = '';
  }
  clearInputField() {
    this.searchInput.nativeElement.value = '';
    this.searchInputMobile.nativeElement.value = '';
  }
  // }

  updateQueryParams() {
    let queryParams: {
      search?: string;
    } = {};

    if (this.searchParam) {
      queryParams.search = encodeURIComponent(this.searchParam);
    }

    console.log(queryParams, 'queryParams');

    // Navigate to the new URL
    this.router.navigate(['/products'], { queryParams });
  }
}
