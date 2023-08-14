import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UserProductService } from 'src/app/features/pos/services/user-product.service';
import { AuthService } from 'src/app/services/auth.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-nav-vendor',
  templateUrl: './nav-vendor.component.html',
  styleUrls: ['./nav-vendor.component.css']
})
export class NavVendorComponent implements OnInit {
  @ViewChild('sidebar') sidebar!: ElementRef;
  @ViewChild('megaMenu') megaMenu!: ElementRef;
  @ViewChild('profileMenu') profileMenu!: ElementRef;
  @ViewChild('searchInput', { static: false }) searchInput!: ElementRef;
  @ViewChild('searchInputMobile', { static: false })
  searchInputMobile!: ElementRef;

  isAuthenticated: boolean = false;
  Message = 'Are you sure you want to log out ?';
  searchQuery: string = '';
  constructor(
    private authService: AuthService,
    public alertService: AlertService,
    private userProductService: UserProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated();
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


  // }
}
