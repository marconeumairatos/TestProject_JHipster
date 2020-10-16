import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserAntwort } from 'app/shared/model/user-antwort.model';

@Component({
  selector: 'jhi-user-antwort-detail',
  templateUrl: './user-antwort-detail.component.html',
})
export class UserAntwortDetailComponent implements OnInit {
  userAntwort: IUserAntwort | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAntwort }) => (this.userAntwort = userAntwort));
  }

  previousState(): void {
    window.history.back();
  }
}
