import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JpollTestModule } from '../../../test.module';
import { AntwortComponent } from 'app/entities/antwort/antwort.component';
import { AntwortService } from 'app/entities/antwort/antwort.service';
import { Antwort } from 'app/shared/model/antwort.model';

describe('Component Tests', () => {
  describe('Antwort Management Component', () => {
    let comp: AntwortComponent;
    let fixture: ComponentFixture<AntwortComponent>;
    let service: AntwortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [AntwortComponent],
      })
        .overrideTemplate(AntwortComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AntwortComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AntwortService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Antwort(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.antworts && comp.antworts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
