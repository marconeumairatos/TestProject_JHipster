import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';

import { IUmfrage } from 'app/shared/model/umfrage.model';

import { IAntwortbyUser, AntwortbyUser } from 'app/shared/model/antwortby-user.model';
import { AntwortbyUserService } from 'app/entities/antwortby-user/antwortby-user.service';
import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from 'app/entities/antwort/antwort.service';

@Component({
  selector: 'jhi-umfrage-detail',
  templateUrl: './umfrage-detail.component.html',
})
export class UmfrageDetailComponent implements OnInit {
  umfrage: IUmfrage | null = null;
  isSaving = false;
  antworts: IAntwort[] = [];

  editForm = this.fb.group({
    id: [],
    userID: [null, null],
    antwort: [],
  });

  constructor(protected activatedRoute: ActivatedRoute, private fb: FormBuilder, protected antwortbyUserService: AntwortbyUserService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ umfrage }) => (this.umfrage = umfrage));
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

  vote(): void {
    this.isSaving = true;
    const antwortbyUser = this.createFromForm();
    if (antwortbyUser.id !== undefined) {
      this.subscribeToSaveResponse(this.antwortbyUserService.update(antwortbyUser));
    } else {
      this.subscribeToSaveResponse(this.antwortbyUserService.create(antwortbyUser));
    }
  }

  private createFromForm(): IAntwortbyUser {
    return {
      ...new AntwortbyUser(),
      id: this.editForm.get(['id'])!.value,
      userID: this.editForm.get(['userID'])!.value,
      antwort: this.editForm.get(['antwort'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAntwortbyUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAntwort): any {
    return item.id;
  }
}
