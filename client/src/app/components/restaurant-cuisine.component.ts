import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Restaurant } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css']
})
export class RestaurantCuisineComponent {
	
	// TODO Task 3
	// For View 2
  restaurants: Restaurant[] = []
  cuisine: string = ''

  constructor(private activatedRoute:ActivatedRoute, private restSvc:RestaurantService){}

  ngOnInit(){
    this.cuisine = this.activatedRoute.snapshot.params['cuisine']
    this.restSvc.getRestaurantsByCuisine(this.cuisine)
              .then(v => {  console.info('check get input: ', v)
                this.restaurants = v })
              .catch(error => console.error('error : ', error))
  }

  getRestaurant(id:string){
    console.info('check id:', id)
  }
}
