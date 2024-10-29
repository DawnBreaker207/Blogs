import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListErrorsComponent } from './shares/component/list-errors/list-errors.component';
import { MarkdownPipe } from './shares/pipes/markdown.pipe';

@NgModule({
  declarations: [AppComponent, ListErrorsComponent, MarkdownPipe],
  imports: [BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
