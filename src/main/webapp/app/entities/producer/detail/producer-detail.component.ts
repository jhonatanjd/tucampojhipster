import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProducer } from '../producer.model';

@Component({
  selector: 'tucam-producer-detail',
  templateUrl: './producer-detail.component.html',
})
export class ProducerDetailComponent implements OnInit {
  producer: IProducer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producer }) => {
      this.producer = producer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
