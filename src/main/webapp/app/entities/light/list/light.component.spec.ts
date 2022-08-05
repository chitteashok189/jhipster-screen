import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LightService } from '../service/light.service';

import { LightComponent } from './light.component';

describe('Light Management Component', () => {
  let comp: LightComponent;
  let fixture: ComponentFixture<LightComponent>;
  let service: LightService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LightComponent],
    })
      .overrideTemplate(LightComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LightComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LightService);

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
    expect(comp.lights?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
