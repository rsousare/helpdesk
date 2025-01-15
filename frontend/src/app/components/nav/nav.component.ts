import { Component, OnInit } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterModule } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { AuthService } from '../../services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    HeaderComponent
  ],
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit{

  constructor(private router: Router, private authService: AuthService, private toast: ToastrService) { }

  ngOnInit(): void {
    this.router.navigate(['home'])
  }

  logout() {
    this.router.navigate(['login'])
    this.authService.logout();
    this.toast.info('Logout realizado com sucesso', 'Logout', {timeOut: 5000})
  }

  /*navigateTo(route: string) {
    this.router.navigate([route]);
  }*/

}
