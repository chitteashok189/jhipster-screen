import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PestService } from '../service/pest.service';

import { PestComponent } from './pest.component';

describe('Pest Management Component', () => {
  let comp: PestComponent;
  let fixture: ComponentFixture<PestComponent>;
  let service: PestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PestComponent],
    })
      .overrideTemplate(PestComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PestComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PestService);

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
    expect(comp.pests?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
