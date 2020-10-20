import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JpollTestModule } from '../../../test.module';
import { AntwortbyUserComponent } from 'app/entities/antwortby-user/antwortby-user.component';
import { AntwortbyUserService } from 'app/entities/antwortby-user/antwortby-user.service';
import { AntwortbyUser } from 'app/shared/model/antwortby-user.model';

describe('Component Tests', () => {
  describe('AntwortbyUser Management Component', () => {
    let comp: AntwortbyUserComponent;
    let fixture: ComponentFixture<AntwortbyUserComponent>;
    let service: AntwortbyUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [AntwortbyUserComponent],
      })
        .overrideTemplate(AntwortbyUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AntwortbyUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AntwortbyUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AntwortbyUser(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.antwortbyUsers && comp.antwortbyUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
