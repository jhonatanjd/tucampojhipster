import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShopping } from '../shopping.model';

@Component({
  selector: 'tucam-shopping-detail',
  templateUrl: './shopping-detail.component.html',
})
export class ShoppingDetailComponent implements OnInit {
  shopping: IShopping | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shopping }) => {
      this.shopping = shopping;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
