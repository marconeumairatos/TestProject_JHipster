import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';

import { IUmfrage } from 'app/shared/model/umfrage.model';
import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from 'app/entities/antwort/antwort.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { AntwortbyUserService } from 'app/entities/antwortby-user/antwortby-user.service';
import { IAntwortbyUser } from 'app/shared/model/antwortby-user.model';

@Component({
  selector: 'jhi-umfrage-detail',
  templateUrl: './umfrage-detail.component.html',
})
export class UmfrageDetailComponent implements OnInit {
  umfrage: IUmfrage | null = null;
  antworts?: IAntwort[];
  editForm = this.fb.group({
    id: [],
    userID: [null, [Validators.required]],
    antwort: [],
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected antwortbyUserService: AntwortbyUserService,
    protected antwortService: AntwortService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ umfrage }) => (this.umfrage = umfrage));
    this.loadAntwortsForUmfrage(this.umfrage?.id!);
  }

  updateForm(antwortbyUser: IAntwortbyUser): void {
    this.editForm.patchValue({
      id: antwortbyUser.id,
      userID: antwortbyUser.userID,
      antwort: antwortbyUser.antwort,
    });
  }

  previousState(): void {
    window.history.back();
  }

  loadAll(): void {
    this.antwortService.query().subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
  }

  loadAntwortsForUmfrage(umfrageId: number): void {
    this.antwortService.findByUmfrageId(umfrageId).subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
  }
}
