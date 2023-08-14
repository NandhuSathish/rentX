import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CartStateService } from 'src/app/shared/states/cart-state.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  currentCount: number = 0;
  tempData:any=[];
  minDate: Date = new Date(2023, 2, 2);

  maxDate!: Date;
  
  range = new FormGroup({
    start: new FormControl<Date | null>(null, Validators.required),
    end: new FormControl<Date | null>(null, Validators.required),
  });
  constructor(private cartService: CartStateService) {}

  ngOnInit(): void {
    this.getCartData();
    const cartCount = localStorage.getItem('cartCount');
    if (cartCount) {
      this.currentCount = parseInt(cartCount, 10);
    }
   // Define an array variable to store the content
const cartItems: any[] = [];

// Push the objects into the array
cartItems.push(
  {
    product: {
      id: 12,
      name: 'Waterbottle',
      category: {
        id: 11,
        name: 'Botawtlqfwe7y',
        createdAt: '2023-04-28T18:32:09.521',
        updatedAt: null,
        status: 0
      },
      subCategory: {
        id: 1,
        name: 'Bottle',
        category: {
          id: 12,
          name: 'Botawtfslqfwe7y',
          createdAt: '2023-04-28T18:32:11.489',
          updatedAt: null,
          status: 0
        },
        createdAt: '2023-04-28T18:46:32.802',
        updatedAt: '2023-04-28T18:46:32.802',
        status: 0
      },
      store: {
        id: 1,
        user: {
          id: 29,
          email: 'admin123@example.com',
          password: '{bcrypt}$2a$10$pps8Wn670AerBwKjRW4ndue8gv5WIqm.cwAFy25k5RdPiOXKL8Xg6',
          phone: '9446162875',
          username: 'sdfsfdsf',
          role: 1,
          type: 0,
          status: 0,
          createdAt: '2023-05-11T07:21:21.056+00:00',
          updatedAt: '2023-05-17T05:02:30.533+00:00'
        },
        mobile: '1122121212212',
        name: '@@@@@*',
        pincode: '121212',
        city: 'aaa',
        state: 'aaa',
        lattitude: '12211343',
        longitude: '121121',
        buildingName: 'aaaaa',
        roadName: 'hgfhfgjfg',
        status: 0,
        createdAt: '2023-05-11T07:24:12.157+00:00',
        updatedAt: '2023-05-11T07:24:12.157+00:00'
      },
      specification: {
        colour: 'gray',
        weight: '65 KG'
      },
      description: 'buahhahaha',
      status: 0,
      stock: 13,
      availableStock: 13,
      price: 5000,
      coverImage:
        'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2F31PIJcknlCS._SS400_.jpg?alt=media&token=ab23a8ee-6a82-4910-a890-22a123578bc2',
      thumbnail:
        'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2Fcompressed_31PIJcknlCS._SS400_.jpg?alt=media&token=39dde37b-4bce-4211-93b2-1fd9d8a991cb',
      createdAt: '2023-06-01T08:39:23.797+00:00',
      updatedAt: '2023-06-01T08:39:23.797+00:00'
    },
    quantity: 1,
    startDate: '2023-06-12T18:30:00.000+00:00',
    endDate: '2023-06-12T18:30:00.000+00:00',
   
  },{ product: {
    id: 12,
    name: 'Shoe',
    category: {
      id: 11,
      name: 'Botawtlqfwe7y',
      createdAt: '2023-04-28T18:32:09.521',
      updatedAt: null,
      status: 0
    },
    subCategory: {
      id: 1,
      name: 'Bottle',
      category: {
        id: 12,
        name: 'Botawtfslqfwe7y',
        createdAt: '2023-04-28T18:32:11.489',
        updatedAt: null,
        status: 0
      },
      createdAt: '2023-04-28T18:46:32.802',
      updatedAt: '2023-04-28T18:46:32.802',
      status: 0
    },
    store: {
      id: 1,
      user: {
        id: 29,
        email: 'admin123@example.com',
        password: '{bcrypt}$2a$10$pps8Wn670AerBwKjRW4ndue8gv5WIqm.cwAFy25k5RdPiOXKL8Xg6',
        phone: '9446162875',
        username: 'sdfsfdsf',
        role: 1,
        type: 0,
        status: 0,
        createdAt: '2023-05-11T07:21:21.056+00:00',
        updatedAt: '2023-05-17T05:02:30.533+00:00'
      },
      mobile: '1122121212212',
      name: '@@@@@*',
      pincode: '121212',
      city: 'aaa',
      state: 'aaa',
      lattitude: '12211343',
      longitude: '121121',
      buildingName: 'aaaaa',
      roadName: 'hgfhfgjfg',
      status: 0,
      createdAt: '2023-05-11T07:24:12.157+00:00',
      updatedAt: '2023-05-11T07:24:12.157+00:00'
    },
    specification: {
      colour: 'gray',
      weight: '65 KG'
    },
    description: 'buahhahaha',
    status: 0,
    stock: 13,
    availableStock: 13,
    price: 5000.0,
    coverImage:
      'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2F16865596710%2Czzzz?alt=media&token=8470dab4-0053-4af5-a431-9d03617b2d22',
    thumbnail:
      'https://firebasestorage.googleapis.com/v0/b/rentx-bb406.appspot.com/o/product-images%2Fcover_1686543374?alt=media&token=688335a1-8c78-46d8-815a-4a12b0a5327c',
    createdAt: '2023-06-01T08:39:23.797+00:00',
    updatedAt: '2023-06-01T08:39:23.797+00:00'
  },
  quantity: 1,
  startDate: '2023-06-12T18:30:00.000+00:00',
  endDate: '2023-06-12T18:30:00.000+00:00',
 
},

  // Add more objects here if needed
);

// Log the array to verify the result
console.log(cartItems);
    this.tempData=cartItems;
    console.log(cartItems)
  }

  getCartData(){
    // this.currentCount=this.tempData.product.cartCount;
    localStorage.setItem('cartCount', this.currentCount.toString());
    this.cartService.updateCartCount(this.currentCount);
  }

  addItemToCart() {
    this.currentCount++;
    // alert(this.currentCount)
    localStorage.setItem('cartCount', this.currentCount.toString());
    this.cartService.updateCartCount(this.currentCount);
  }

  removeItemFromCart() {
    if (this.currentCount > 0) {
      this.currentCount--;
      localStorage.setItem('cartCount', this.currentCount.toString());
      this.cartService.updateCartCount(this.currentCount);
    }
  }

  dateFilterFn = (date: Date) => ![0, 6].includes(date.getDay());
  getImageUrl(image: any, index: number): string {
    const imageKey = 'image' + (index + 1);
    return image[imageKey];
  }

}


