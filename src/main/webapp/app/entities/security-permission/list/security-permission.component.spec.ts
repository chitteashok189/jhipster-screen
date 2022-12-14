import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SecurityPermissionService } from '../service/security-permission.service';

import { SecurityPermissionComponent } from './security-permission.component';

describe('SecurityPermission Management Component', () => {
  let comp: SecurityPermissionComponent;
  let fixture: ComponentFixture<SecurityPermissionComponent>;
  let service: SecurityPermissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SecurityPermissionComponent],
    })
      .overrideTemplate(SecurityPermissionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecurityPermissionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SecurityPermissionService);

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
    expect(comp.securityPermissions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
