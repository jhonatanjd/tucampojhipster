import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderDetai } from '../order-detai.model';

@Component({
  selector: 'tucam-order-detai-detail',
  templateUrl: './order-detai-detail.component.html',
})
export class OrderDetaiDetailComponent implements OnInit {
  orderDetai: IOrderDetai | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderDetai }) => {
      this.orderDetai = orderDetai;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
