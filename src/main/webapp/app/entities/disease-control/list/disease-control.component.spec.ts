import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DiseaseControlService } from '../service/disease-control.service';

import { DiseaseControlComponent } from './disease-control.component';

describe('DiseaseControl Management Component', () => {
  let comp: DiseaseControlComponent;
  let fixture: ComponentFixture<DiseaseControlComponent>;
  let service: DiseaseControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DiseaseControlComponent],
    })
      .overrideTemplate(DiseaseControlComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DiseaseControlComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DiseaseControlService);

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
    expect(comp.diseaseControls?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
