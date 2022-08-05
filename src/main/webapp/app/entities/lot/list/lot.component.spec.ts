import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LotService } from '../service/lot.service';

import { LotComponent } from './lot.component';

describe('Lot Management Component', () => {
  let comp: LotComponent;
  let fixture: ComponentFixture<LotComponent>;
  let service: LotService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LotComponent],
    })
      .overrideTemplate(LotComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LotComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LotService);

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
    expect(comp.lots?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
