import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDats } from '../dats.model';

@Component({
  selector: 'tucam-dats-detail',
  templateUrl: './dats-detail.component.html',
})
export class DatsDetailComponent implements OnInit {
  dats: IDats | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dats }) => {
      this.dats = dats;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
