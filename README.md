#Scavenge 1.16.5

A Block Click Script Generator using Json.
This is the API made public. You can use it AS IS and can only redistribute it with your addon's.
Though in addon's should not contain API files due to leading to bugs if the API is not up to date.

How to use the API:

- Create a Class that extends IScavengePlugin.class and has a @ScavengePlugin Annotation. The Plugin will load Automatically. No Mod File Required.
- Through the ScavengeRegistry.class you can register Condition & Effects. Please Use Base Classes for Creation of things. (BaseScavengeCondition & BaseScavengeEffect)
- Builders now contain everything instead of the Implementation of the effect/condition. Serialization/Deserialization/Networking/Documentation Generation.

If something is unclear:    
- ask the dev (Speiger) for permission. 
