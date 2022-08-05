import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DeviceModelService } from '../service/device-model.service';

import { DeviceModelComponent } from './device-model.component';

describe('DeviceModel Management Component', () => {
  let comp: DeviceModelComponent;
  let fixture: ComponentFixture<DeviceModelComponent>;
  let service: DeviceModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DeviceModelComponent],
    })
      .overrideTemplate(DeviceModelComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeviceModelComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DeviceModelService);

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
    expect(comp.deviceModels?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
