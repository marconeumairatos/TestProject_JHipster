import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JpollTestModule } from '../../../test.module';
import { UserantwortComponent } from 'app/entities/userantwort/userantwort.component';
import { UserantwortService } from 'app/entities/userantwort/userantwort.service';
import { Userantwort } from 'app/shared/model/userantwort.model';

describe('Component Tests', () => {
  describe('Userantwort Management Component', () => {
    let comp: UserantwortComponent;
    let fixture: ComponentFixture<UserantwortComponent>;
    let service: UserantwortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UserantwortComponent],
      })
        .overrideTemplate(UserantwortComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserantwortComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserantwortService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Userantwort(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userantworts && comp.userantworts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
