## Endpoint

https://stormy-river-83187.herokuapp.com/

## APIs

### Get branch details given the IFSC code

GET /api/branches/${ifsc}

Response:

```
{
  bankId: $bankId,
  ifsc: $ifscCode,
  branch: $branchName,
  addr: $branchAddress,
  city: $city,
  district: $district
  state: $state,
  bankName: $bankName
}
```

### Get details of all branches of a bank in a city

GET /api/branches/$bankName/cities/$city

Response:

```
{
  "branches" : [
    {
      // branch details as above
    },
    ...
  ]
}
```

## Storage

The service uses an in-memory data store built from the CSV file by default. If you'd like to read data from the database,
set the header X-Demo-Data-Source to the value 'db'.
