import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { HarvestService } from '../service/harvest.service';

import { HarvestComponent } from './harvest.component';

describe('Harvest Management Component', () => {
  let comp: HarvestComponent;
  let fixture: ComponentFixture<HarvestComponent>;
  let service: HarvestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [HarvestComponent],
    })
      .overrideTemplate(HarvestComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HarvestComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HarvestService);

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
    expect(comp.harvests?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
