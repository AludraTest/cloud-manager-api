/*
 * Copyright (C) 2010-2015 AludraTest.org and the contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aludratest.cloud.resourcegroup;

/**
 * Interface for natures which can be added to resource groups. <br>
 * The concept of <i>Group Natures</i> allows for an extensibility of resource groups without having to reimplement or change
 * existing code. An example is a <i>Cloud Nature</i> for a resource type, where resources are allocated or deallocated in a Cloud
 * Service, depending e.g. on current resource group usage load. <br>
 * A nature is assigned to an existing resource group by creating a {@link ResourceGroupNatureAssociation} for this group. The
 * association can implement <code>Configurable</code> to allow nature-specific configuration of the group.
 * 
 * @author falbrech
 * 
 */
public interface ResourceGroupNature {

	/**
	 * Plexus role of this component.
	 */
	public static final String ROLE = ResourceGroupNature.class.getName();

	/**
	 * Returns a unique technical name for this nature, which is used for internal association purposes.
	 * The name should match the Plexus hint used for the implementation class.
	 * 
	 * @return A unique technical name for this nature, never <code>null</code>.
	 */
	public String getName();
	
	/**
	 * Returns a human-readable name for this nature, e.g. for display purposes.
	 * 
	 * @return A human-readable name for this nature, never <code>null</code>.
	 */
	public String getDisplayName();

	/**
	 * Returns <code>true</code> if this nature could be associated to the given group, and <code>false</code> otherwise.
	 * 
	 * @param group
	 *            Group to check.
	 * 
	 * @return <code>true</code> if this nature could be associated to the given group, and <code>false</code> otherwise.
	 */
	public boolean isAvailableFor(ResourceGroup group);
	
	/**
	 * Creates an association of this nature to the given resource group. If {@link #isAvailableFor(ResourceGroup)} returned
	 * <code>false</code> for the given resource group, the behaviour of this method is unspecified (e.g. you could return
	 * <code>null</code>, or throw any Exception).
	 * 
	 * @param group
	 *            Group to create an association for.
	 * 
	 * @return An association of this nature with the given group.
	 */
	public ResourceGroupNatureAssociation createAssociationFor(ResourceGroup group);

}
