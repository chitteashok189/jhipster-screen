import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SeedService } from '../service/seed.service';

import { SeedComponent } from './seed.component';

describe('Seed Management Component', () => {
  let comp: SeedComponent;
  let fixture: ComponentFixture<SeedComponent>;
  let service: SeedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SeedComponent],
    })
      .overrideTemplate(SeedComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SeedComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SeedService);

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
    expect(comp.seeds?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
