import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserAntwort, UserAntwort } from 'app/shared/model/user-antwort.model';
import { UserAntwortService } from './user-antwort.service';

@Component({
  selector: 'jhi-user-antwort-update',
  templateUrl: './user-antwort-update.component.html',
})
export class UserAntwortUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userID: [null, [Validators.required]],
  });

  constructor(protected userAntwortService: UserAntwortService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAntwort }) => {
      this.updateForm(userAntwort);
    });
  }

  updateForm(userAntwort: IUserAntwort): void {
    this.editForm.patchValue({
      id: userAntwort.id,
      userID: userAntwort.userID,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userAntwort = this.createFromForm();
    if (userAntwort.id !== undefined) {
      this.subscribeToSaveResponse(this.userAntwortService.update(userAntwort));
    } else {
      this.subscribeToSaveResponse(this.userAntwortService.create(userAntwort));
    }
  }

  private createFromForm(): IUserAntwort {
    return {
      ...new UserAntwort(),
      id: this.editForm.get(['id'])!.value,
      userID: this.editForm.get(['userID'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAntwort>>): void {
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
}
