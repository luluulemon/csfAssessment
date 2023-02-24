import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { CuisineListComponent } from './components/cuisine-list.component';
import { RestaurantCuisineComponent } from './components/restaurant-cuisine.component';
import { RestaurantDetailsComponent } from './components/restaurant-details.component';

import { Routes, RouterModule } from '@angular/router'
import { HttpClientModule } from '@angular/common/http'
import { RestaurantService } from './restaurant-service';

const appRoutes:Routes = [
  { path: '', component: CuisineListComponent },
  { path: 'restaurants/:cuisine', component:RestaurantCuisineComponent},
  { path: 'restDetails', component:RestaurantDetailsComponent },
  { path: 'restDetails/:cuisine', component:RestaurantDetailsComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    CuisineListComponent,
    RestaurantCuisineComponent,
    RestaurantDetailsComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash:true} ),
    HttpClientModule
  ],
  providers: [RestaurantService],
  bootstrap: [AppComponent]
})
export class AppModule { }
