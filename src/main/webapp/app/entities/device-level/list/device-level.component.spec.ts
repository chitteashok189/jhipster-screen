import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DeviceLevelService } from '../service/device-level.service';

import { DeviceLevelComponent } from './device-level.component';

describe('DeviceLevel Management Component', () => {
  let comp: DeviceLevelComponent;
  let fixture: ComponentFixture<DeviceLevelComponent>;
  let service: DeviceLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DeviceLevelComponent],
    })
      .overrideTemplate(DeviceLevelComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeviceLevelComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DeviceLevelService);

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
    expect(comp.deviceLevels?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
