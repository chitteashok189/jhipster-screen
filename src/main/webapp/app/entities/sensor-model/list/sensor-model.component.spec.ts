import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SensorModelService } from '../service/sensor-model.service';

import { SensorModelComponent } from './sensor-model.component';

describe('SensorModel Management Component', () => {
  let comp: SensorModelComponent;
  let fixture: ComponentFixture<SensorModelComponent>;
  let service: SensorModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SensorModelComponent],
    })
      .overrideTemplate(SensorModelComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SensorModelComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SensorModelService);

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
    expect(comp.sensorModels?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
