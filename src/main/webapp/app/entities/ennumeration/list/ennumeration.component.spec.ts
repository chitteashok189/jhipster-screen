import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EnnumerationService } from '../service/ennumeration.service';

import { EnnumerationComponent } from './ennumeration.component';

describe('Ennumeration Management Component', () => {
  let comp: EnnumerationComponent;
  let fixture: ComponentFixture<EnnumerationComponent>;
  let service: EnnumerationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EnnumerationComponent],
    })
      .overrideTemplate(EnnumerationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnnumerationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EnnumerationService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.ennumerations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
