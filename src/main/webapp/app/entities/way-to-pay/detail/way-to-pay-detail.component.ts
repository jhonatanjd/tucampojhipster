import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWayToPay } from '../way-to-pay.model';

@Component({
  selector: 'tucam-way-to-pay-detail',
  templateUrl: './way-to-pay-detail.component.html',
})
export class WayToPayDetailComponent implements OnInit {
  wayToPay: IWayToPay | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wayToPay }) => {
      this.wayToPay = wayToPay;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
