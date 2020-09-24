import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { UmfrageDetailComponent } from 'app/entities/umfrage/umfrage-detail.component';
import { Umfrage } from 'app/shared/model/umfrage.model';

describe('Component Tests', () => {
  describe('Umfrage Management Detail Component', () => {
    let comp: UmfrageDetailComponent;
    let fixture: ComponentFixture<UmfrageDetailComponent>;
    const route = ({ data: of({ umfrage: new Umfrage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UmfrageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UmfrageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UmfrageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load umfrage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.umfrage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
