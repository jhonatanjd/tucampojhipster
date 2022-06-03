import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnits } from '../units.model';

@Component({
  selector: 'tucam-units-detail',
  templateUrl: './units-detail.component.html',
})
export class UnitsDetailComponent implements OnInit {
  units: IUnits | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ units }) => {
      this.units = units;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
