import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserAntwort, UserAntwort } from 'app/shared/model/user-antwort.model';
import { UserAntwortService } from './user-antwort.service';
import { IUmfrage } from 'app/shared/model/umfrage.model';
import { UmfrageService } from 'app/entities/umfrage/umfrage.service';
import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from 'app/entities/antwort/antwort.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

type SelectableEntity = IUmfrage | IAntwort;

@Component({
  selector: 'jhi-user-antwort-update',
  templateUrl: './user-antwort-update.component.html',
})
export class UserAntwortUpdateComponent implements OnInit {
  isSaving = false;
  umfrages: IUmfrage[] = [];
  antworts: IAntwort[] = [];
  account!: Account;

  editForm = this.fb.group({
    id: [],
    user: [null, [Validators.required]],
    umfrage: [],
    antwort: [],
  });

  constructor(
    protected userAntwortService: UserAntwortService,
    protected umfrageService: UmfrageService,
    protected antwortService: AntwortService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAntwort }) => {
      this.updateForm(userAntwort);

      this.umfrageService.query().subscribe((res: HttpResponse<IUmfrage[]>) => (this.umfrages = res.body || []));

      this.antwortService.query().subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
    });
  }

  updateForm(userAntwort: IUserAntwort): void {
    this.editForm.patchValue({
      id: userAntwort.id,
      // user: userAntwort.user,
      umfrage: userAntwort.umfrage,
      antwort: userAntwort.antwort,
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
      user: this.editForm.get(['user'])!.value,
      umfrage: this.editForm.get(['umfrage'])!.value,
      antwort: this.editForm.get(['antwort'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
