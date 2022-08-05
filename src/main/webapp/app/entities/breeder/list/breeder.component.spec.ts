import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BreederService } from '../service/breeder.service';

import { BreederComponent } from './breeder.component';

describe('Breeder Management Component', () => {
  let comp: BreederComponent;
  let fixture: ComponentFixture<BreederComponent>;
  let service: BreederService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BreederComponent],
    })
      .overrideTemplate(BreederComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BreederComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BreederService);

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
    expect(comp.breeders?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
