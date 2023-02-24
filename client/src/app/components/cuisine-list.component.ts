import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent {

	// TODO Task 2
	// For View 1
  cuisines: string[] = []

  constructor(private restSvc:RestaurantService, private router:Router){}

  ngOnInit(){
    this.restSvc.getCuisineList()
              .then(v => this.cuisines = v)
              .catch(error => console.error('error: ', error))
  }

  getCuisine(cuisine: string){
    console.info('check cuisine', cuisine)
    this.router.navigate(['restaurants', cuisine])
  }
}
