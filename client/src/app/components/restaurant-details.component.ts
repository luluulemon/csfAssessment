import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant, Comment } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent {
	
	// TODO Task 4 and Task 5
	// For View 3
  rest!: Restaurant
  form!: FormGroup
  cuisine: string = ''

  constructor(private restSvc:RestaurantService, private fb:FormBuilder,
              private router:Router, private activatedRoute:ActivatedRoute){}

  ngOnInit(){
    this.rest = this.restSvc.rest
    this.createForm()
    this.cuisine = this.activatedRoute.snapshot.params['cuisine']
  }

  createForm(){
    this.form = this.fb.group({
      name: this.fb.control<string>('', [Validators.required, Validators.minLength(4)]),
      rating: this.fb.control<string>('', Validators.required),
      text: this.fb.control<string>('', Validators.required)
    })
  }

  submitComments(){
    
    this.form.value.restaurantId = this.rest.restaurantId
    const comment:Comment = this.form.value
    console.info('check comment:', comment)
    this.restSvc.postComment(comment)
                  .then( v => 
                    { console.info('check post response: ', v)
                      this.router.navigate([''])})
                  .catch(error => console.error('error: ',error))
  }
}
