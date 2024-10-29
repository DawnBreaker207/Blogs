import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth/auth.component';
import { IfAuthenticatedDirective } from './auth/if-authenticated.directive';
import { HeaderComponent } from './layout/header.component';
import { FooterComponent } from './layout/footer.component';

@NgModule({
  declarations: [
    AuthComponent,
    IfAuthenticatedDirective,
    HeaderComponent,
    FooterComponent,
  ],
  imports: [CommonModule],
  exports: [AuthComponent],
})
export class CoreModule {}
