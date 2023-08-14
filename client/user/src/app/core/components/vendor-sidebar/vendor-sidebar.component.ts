import {
  Component,
  ElementRef,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { AlertService } from 'src/app/shared/services/alert.service';

@Component({
  selector: 'app-vendor-sidebar',
  templateUrl: './vendor-sidebar.component.html',
  styleUrls: ['./vendor-sidebar.component.css'],
})
export class VendorSidebarComponent implements OnInit {
  @ViewChild('sidebar') sidebar!: ElementRef;
  Message="Are you sure you want to log out?";
  collapsed = false;
  showArrow=false;
  iconSidebarClicked=false;
  
  ngOnInit(): void {}

  constructor(private renderer: Renderer2,private authService : AuthService, private alertService:AlertService) {}


  toggleSidebar() {
    const sidebarEl = this.sidebar.nativeElement;
    sidebarEl.classList.toggle('hidden');
  }
  logOut(){
    this.alertService.openAlert(this.Message).subscribe((res) => {
      if (res) {
        this.authService.logOut();
        this.ngOnInit();
      } else {
        return;
      }
    });
  }
  toggleSidebar2() {
    this.collapsed = !this.collapsed;
  }
  iconSidebar(){
this.iconSidebarClicked=true;
  }
  staticSidebar(){

    this.iconSidebarClicked=false;
    }
    reloadWindow() {
      window.location.reload();
    }
    
  }

