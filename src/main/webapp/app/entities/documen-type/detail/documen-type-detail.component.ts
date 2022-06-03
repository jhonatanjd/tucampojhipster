import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumenType } from '../documen-type.model';

@Component({
  selector: 'tucam-documen-type-detail',
  templateUrl: './documen-type-detail.component.html',
})
export class DocumenTypeDetailComponent implements OnInit {
  documenType: IDocumenType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documenType }) => {
      this.documenType = documenType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
