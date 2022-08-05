import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { WeatherService } from '../service/weather.service';

import { WeatherComponent } from './weather.component';

describe('Weather Management Component', () => {
  let comp: WeatherComponent;
  let fixture: ComponentFixture<WeatherComponent>;
  let service: WeatherService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [WeatherComponent],
    })
      .overrideTemplate(WeatherComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WeatherComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(WeatherService);

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
    expect(comp.weathers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
