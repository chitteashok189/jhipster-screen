import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { EnnumerationTypeService } from '../service/ennumeration-type.service';

import { EnnumerationTypeComponent } from './ennumeration-type.component';

describe('EnnumerationType Management Component', () => {
  let comp: EnnumerationTypeComponent;
  let fixture: ComponentFixture<EnnumerationTypeComponent>;
  let service: EnnumerationTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EnnumerationTypeComponent],
    })
      .overrideTemplate(EnnumerationTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnnumerationTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EnnumerationTypeService);

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
    expect(comp.ennumerationTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
