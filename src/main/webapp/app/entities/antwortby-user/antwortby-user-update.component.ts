import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAntwortbyUser, AntwortbyUser } from 'app/shared/model/antwortby-user.model';
import { AntwortbyUserService } from './antwortby-user.service';
import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from 'app/entities/antwort/antwort.service';

@Component({
  selector: 'jhi-antwortby-user-update',
  templateUrl: './antwortby-user-update.component.html',
})
export class AntwortbyUserUpdateComponent implements OnInit {
  isSaving = false;
  antworts: IAntwort[] = [];

  editForm = this.fb.group({
    id: [],
    userID: [null, [Validators.required]],
    antwort: [],
  });

  constructor(
    protected antwortbyUserService: AntwortbyUserService,
    protected antwortService: AntwortService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antwortbyUser }) => {
      this.updateForm(antwortbyUser);

      this.antwortService.query().subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
    });
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

  save(): void {
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
