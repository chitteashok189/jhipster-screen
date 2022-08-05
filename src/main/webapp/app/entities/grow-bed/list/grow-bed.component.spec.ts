import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { GrowBedService } from '../service/grow-bed.service';

import { GrowBedComponent } from './grow-bed.component';

describe('GrowBed Management Component', () => {
  let comp: GrowBedComponent;
  let fixture: ComponentFixture<GrowBedComponent>;
  let service: GrowBedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [GrowBedComponent],
    })
      .overrideTemplate(GrowBedComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GrowBedComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GrowBedService);

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
    expect(comp.growBeds?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
