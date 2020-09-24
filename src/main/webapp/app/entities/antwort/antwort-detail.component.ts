import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAntwort } from 'app/shared/model/antwort.model';

@Component({
  selector: 'jhi-antwort-detail',
  templateUrl: './antwort-detail.component.html',
})
export class AntwortDetailComponent implements OnInit {
  antwort: IAntwort | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antwort }) => (this.antwort = antwort));
  }

  previousState(): void {
    window.history.back();
  }
}
