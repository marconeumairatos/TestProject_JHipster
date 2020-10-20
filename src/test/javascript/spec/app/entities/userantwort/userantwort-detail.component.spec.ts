import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { UserantwortDetailComponent } from 'app/entities/userantwort/userantwort-detail.component';
import { Userantwort } from 'app/shared/model/userantwort.model';

describe('Component Tests', () => {
  describe('Userantwort Management Detail Component', () => {
    let comp: UserantwortDetailComponent;
    let fixture: ComponentFixture<UserantwortDetailComponent>;
    const route = ({ data: of({ userantwort: new Userantwort(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UserantwortDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserantwortDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserantwortDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userantwort on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userantwort).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
