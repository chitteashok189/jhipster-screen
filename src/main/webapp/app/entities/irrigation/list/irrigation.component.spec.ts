import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { IrrigationService } from '../service/irrigation.service';

import { IrrigationComponent } from './irrigation.component';

describe('Irrigation Management Component', () => {
  let comp: IrrigationComponent;
  let fixture: ComponentFixture<IrrigationComponent>;
  let service: IrrigationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [IrrigationComponent],
    })
      .overrideTemplate(IrrigationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IrrigationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(IrrigationService);

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
    expect(comp.irrigations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
