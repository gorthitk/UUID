# UUID

Library with multiple implementations for supporting UUID creation in Distributed systems.


## Snowflake UUID

Implementation of the Twitter Snowflake UUID : https://blog.x.com/engineering/en_us/a/2010/announcing-snowflake

### What was proposed 

| 0  (Always) | Epoch (Timestamp)                               | Datacenter ID | Machine ID  | Sequence ID         | 
|-------------|-------------------------------------------------|---------------|-------------|---------------------|
| 1 Bit       | ------------------41 Bits---------------------- | -- 5 Bits--   | ---5 Bits-- | ------12 Bits------ |

### What is implemented
Since, the local implementation is not deployed on a Datacenter, the Mac address is instead represented using 10 Bits

| 0  (Always) | Epoch (Timestamp)                               | Machine ID          | Sequence ID         | 
|-------------|-------------------------------------------------|---------------------|---------------------|
| 1 Bit       | ------------------41 Bits---------------------- | ----- 10 Bits ----- | ------12 Bits------ |