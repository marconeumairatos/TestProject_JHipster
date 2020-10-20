import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { AntwortbyUserDetailComponent } from 'app/entities/antwortby-user/antwortby-user-detail.component';
import { AntwortbyUser } from 'app/shared/model/antwortby-user.model';

describe('Component Tests', () => {
  describe('AntwortbyUser Management Detail Component', () => {
    let comp: AntwortbyUserDetailComponent;
    let fixture: ComponentFixture<AntwortbyUserDetailComponent>;
    const route = ({ data: of({ antwortbyUser: new AntwortbyUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [AntwortbyUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AntwortbyUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AntwortbyUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load antwortbyUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.antwortbyUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
