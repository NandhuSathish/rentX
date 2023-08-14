import { Component, OnInit } from '@angular/core';
import { UserProductService } from '../../services/user-product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EventEmitterService } from 'src/app/services/event-emitter.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
})
export class ProductsComponent implements OnInit {
  categoryFilterParams: string[] = [];
  StoreFilterParams: string[] = [];
  searchParam!: string;
  pageNum!: number;
  sortParam!: string;
  sortOrder!: string;
  selectedCategoryFilter: string[] = [];
  selectedStoreFilter: string[] = [];
  productList: any[] = [];
  isLoading = true;
  constructor(
    private productService: UserProductService,
    private activatedRoute: ActivatedRoute,
    private eventEmitter: EventEmitterService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.searchParam = params['search'];
      this.pageNum = params['page'];
      this.sortParam = params['sortBy'];
      this.sortOrder = params['sortIn'];

      this.categoryFilterParams = (
        params['categoryFilterParams']
          ? params['categoryFilterParams'].split('_')
          : []
      )
        .map((item: any) => parseInt(item.split(':')[1]))
        .join(',');

      this.StoreFilterParams = (
        params['storeFilterParams']
          ? params['storeFilterParams'].split('_')
          : []
      )
        .map((item: any) => parseInt(item.split(':')[1]))
        .join(',');

      // Call API with the received parameters
      this.loadData();
    });
  }

  loadData() {
    this.isLoading = true;
    this.productService
      .listProduct(
        this.pageNum,
        undefined,
        this.sortParam,
        this.sortOrder,
        this.searchParam,
        this.categoryFilterParams.toString(),
        this.StoreFilterParams.toString()
      )
      .subscribe((res) => {
        this.productList = res.result;
        this.eventEmitter.onLoadEvent();
        setTimeout(() => {
          this.isLoading = false;
        }, 1000);
        console.log(this.productList, ':productList');
      });
  }

  goToDetails(product: any) {
    let queryParams: {
      // productNameParams?: string;
      pid?: number;
    } = {};
    // queryParams.productNameParams = product.name;
    queryParams.pid = product.id;
    this.router.navigate(['/detail', product.name], { queryParams });
  }

  handleAddToCart(event: MouseEvent) {
    event.stopPropagation();
    console.log('Clicked on Div Inner carted');
  }
}
