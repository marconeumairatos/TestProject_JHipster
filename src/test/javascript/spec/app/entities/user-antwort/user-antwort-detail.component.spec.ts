import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { UserAntwortDetailComponent } from 'app/entities/user-antwort/user-antwort-detail.component';
import { UserAntwort } from 'app/shared/model/user-antwort.model';

describe('Component Tests', () => {
  describe('UserAntwort Management Detail Component', () => {
    let comp: UserAntwortDetailComponent;
    let fixture: ComponentFixture<UserAntwortDetailComponent>;
    const route = ({ data: of({ userAntwort: new UserAntwort(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UserAntwortDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAntwortDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAntwortDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userAntwort on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userAntwort).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
