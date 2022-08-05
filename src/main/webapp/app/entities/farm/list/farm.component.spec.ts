import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { FarmService } from '../service/farm.service';

import { FarmComponent } from './farm.component';

describe('Farm Management Component', () => {
  let comp: FarmComponent;
  let fixture: ComponentFixture<FarmComponent>;
  let service: FarmService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FarmComponent],
    })
      .overrideTemplate(FarmComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FarmComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FarmService);

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
    expect(comp.farms?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
