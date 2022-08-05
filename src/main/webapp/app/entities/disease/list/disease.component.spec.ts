import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DiseaseService } from '../service/disease.service';

import { DiseaseComponent } from './disease.component';

describe('Disease Management Component', () => {
  let comp: DiseaseComponent;
  let fixture: ComponentFixture<DiseaseComponent>;
  let service: DiseaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DiseaseComponent],
    })
      .overrideTemplate(DiseaseComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiseaseComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DiseaseService);

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
    expect(comp.diseases?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
